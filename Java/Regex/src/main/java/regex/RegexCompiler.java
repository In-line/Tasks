package regex;

import regex.exception.RegexCompilerError;
import regex.token.StringToken;
import regex.token.adaptor.AbstractAdaptor;
import regex.token.adaptor.NegateAdaptor;
import regex.token.adaptor.PlusAdaptor;
import regex.token.adaptor.StarAdaptor;
import regex.token.facade.GroupFacade;
import regex.token.single.*;
import regex.token.single.special.BeginToken;
import regex.token.single.special.EndToken;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RegexCompiler {
    private String regex;
    private List<IToken> tokenList;

    private static class RegexStringIterator implements ListIterator<Character> {
        private String string;
        private int pos = 0;


        public RegexStringIterator(final String string, int pos) {
            this.string = string;
            this.pos = pos;
        }

        public RegexStringIterator(final String string) {
            this(string, 0);
        }

        public RegexStringIterator(final RegexStringIterator other) {
            this(other.string, other.pos);
        }

        public RegexStringIterator assign(final RegexStringIterator other) {
            this.string = other.string;
            this.pos = other.pos;
            return this;
        }

        public char currentCharacter() {
            return string.charAt(pos);
        }

        public int curPos() {
            return pos;
        }

        public char readAhead() {
            int nextPos = pos + 1;

            if(nextPos < string.length()) {
                return string.charAt(nextPos);
            } else {
                throw new RegexCompilerError(nextPos);
            }
        }

        @Override
        public boolean hasNext() {
            return pos < string.length();
        }

        @Override
        public Character next() {
            if (!this.hasNext()) {
                throw new RegexCompilerError(pos);
            }

            return string.charAt(pos++);
        }

        @Override
        public boolean hasPrevious() {
            return pos >= 0;
        }

        @Override
        public Character previous() {
            return string.charAt(pos--);
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Character character) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Character character) {
            throw new UnsupportedOperationException();
        }

    }

    ;

    public RegexCompiler(final String regex) {
        this.regex = regex;
        this.tokenList = fullCompile(new RegexStringIterator(regex));
    }

    public List<IToken> getCompiled() {
        return tokenList;
    }

    private class StringTokenAccumulator {
        private StringBuilder builder;

        public StringTokenAccumulator() {
            this.builder = new StringBuilder("");
        }

        public IToken releaseAccumulatedStringToken() {
            String string = builder.toString();
            if (!string.isEmpty()) {
                builder.setLength(0);
                return new StringToken(string);
            }

            return null;
        }

        private void accumulateStringToken(RegexStringIterator iterator) {
            builder.append(iterator.currentCharacter());
        }
    }

    private List<IToken> fullCompile(RegexStringIterator startIterator) {
        List<IToken> list = new ArrayList<>();

        StringTokenAccumulator stringTokenAccumulator = new StringTokenAccumulator();
        AdaptorTokens adaptorTokens = new AdaptorTokens(list);
        GroupTokens groupTokens = new GroupTokens();

        for (RegexStringIterator iterator = startIterator; iterator.hasNext(); iterator.next()) {
            if (groupTokens.handleGroupEnd(iterator)) {
                break;
            }

            if (addIfSuccessful(list, groupTokens.compileGroupStart(iterator))) { // Recursive strategy.
                continue;
            }

            if(addIfSuccessful(list, CharacterClassTokens.compileCharacterClass(iterator))) {
                continue;
            }

            IToken token = SimpleTokens.compileSimpleTokens(iterator);
            if (token != null) {
                addIfSuccessful(list, stringTokenAccumulator.releaseAccumulatedStringToken());
                list.add(token);
                continue;
            }

            if (!AdaptorTokens.canCreateAdaptor(iterator)) {
                stringTokenAccumulator.accumulateStringToken(iterator);
                continue;
            }

            addIfSuccessful(list, stringTokenAccumulator.releaseAccumulatedStringToken());
            if (!adaptorTokens.compileAdaptorTokens(iterator)) {
                stringTokenAccumulator.accumulateStringToken(iterator);
                // TODO: Exception? Adaptor without previous token...
            }

        }

        addIfSuccessful(list, stringTokenAccumulator.releaseAccumulatedStringToken());

        return list;
    }

    private static class SimpleTokens {
        private SimpleTokens() {
        }

        public static IToken compileSimpleTokens(RegexStringIterator iterator) {
            IToken token = null;

            if ((token = createBasicToken(iterator)) != null) {
                return token;
            }

            if ((token = createMetaSequenceToken(iterator)) != null) {
                return token;
            }

            return null;
        }

        private static IToken createBasicToken(RegexStringIterator iterator) {
            switch (iterator.currentCharacter()) {
                case '^':
                    return new BeginToken();
                case '$':
                    return new EndToken();
                case '.':
                    return new DotToken();
                case '?':
                    return new QuestionMarkToken();
            }

            return null;
        }

        private static IToken createMetaSequenceTokenImpl(RegexStringIterator iterator) {
            switch (iterator.currentCharacter()) { // NOTE: Important. We need only character after '\\'
                case 'd':
                    return new DigitToken();
                case 'D':
                    return new NegateAdaptor(new DigitToken());
                case 'w':
                    return new AlphaToken();
                case 'W':
                    return new NegateAdaptor(new AlphaToken());
                case 's':
                    return new WhiteSpaceToken();
                case 'S':
                    return new NegateAdaptor(new WhiteSpaceToken());
            }
            return null;
        }

        private static IToken createMetaSequenceToken(RegexStringIterator iterator) {
            if (iterator.currentCharacter() == '\\' && iterator.hasNext()) {
                iterator.next();
                IToken token = createMetaSequenceTokenImpl(iterator);
                if (token == null) {
                    iterator.previous(); // Oh, unexpected
                }
                return token;
            }
            return null;
        }
    }

    private static class AdaptorTokens {
        private List<IToken> tokenList;

        public AdaptorTokens(List<IToken> tokenList) {
            this.tokenList = tokenList;
        }

        private static IToken createAdaptorObjects(char currentCharacter, IToken previousToken) {
            AbstractAdaptor adaptor = null;
            switch (currentCharacter) {
                case '+':
                    adaptor = new PlusAdaptor(previousToken);
                    break;
                case '*':
                    adaptor = new StarAdaptor(previousToken);
                    break;
            }

            return adaptor;
        }

        public boolean compileAdaptorTokens(RegexStringIterator iterator) {
            if (!tokenList.isEmpty()) {
                int lastItemIndex = tokenList.size() - 1;
                tokenList.set(
                        lastItemIndex,
                        createAdaptorObjects(
                                iterator.currentCharacter(),
                                tokenList.get(lastItemIndex)
                        )
                );

                return true;
            }

            return false;
        }

        public static boolean canCreateAdaptor(RegexStringIterator iterator) {
            return createAdaptorObjects(iterator.currentCharacter(), null) != null;
        }
    }

    private class GroupTokens {
        public IToken compileGroupStart(RegexStringIterator iterator) {
            if (iterator.currentCharacter() == '(') {
                iterator.next();
                return new GroupFacade(fullCompile(iterator));
            }
            return null;
        }

        public boolean handleGroupEnd(RegexStringIterator iterator) {
            if (iterator.currentCharacter() == ')') {
                iterator.next();
                return true;
            }
            return false;
        }
    }

    private static class CharacterClassTokens {
        public static GroupFacade compileCharacterClass(RegexStringIterator iterator) {
            // TODO: Error reporting.
            List<IToken> tokens = new ArrayList<>();

            if (iterator.currentCharacter() == '[') {
                iterator.next();
            } else {
                return null;
            }

            while (iterator.hasNext()) {
                if (iterator.currentCharacter() == ']') {
                    return tokens.isEmpty() ? null : new GroupFacade(tokens);
                }

                if(iterator.readAhead() == '-') {
                    RegexStringIterator iteratorCopy = new RegexStringIterator(iterator);
                    iteratorCopy.next(); // Skip '-'
                    if(iteratorCopy.next() != ']') {
                        tokens.add(new CharacterRangeToken(iterator.currentCharacter(), iteratorCopy.currentCharacter()));
                        iterator.assign(iteratorCopy);
                        iterator.next(); // Skip end character.
                        continue;
                    }
                }

                tokens.add(new OneCharacterToken(iterator.currentCharacter()));
                iterator.next();
            }

            // '[' without ']'
            throw new RegexCompilerError(iterator.curPos());
        }
    }

    private boolean addIfSuccessful(List<IToken> list, IToken token) {
        if (token == null) {
            return false;
        } else {
            list.add(token);
            return true;
        }
    }

}
