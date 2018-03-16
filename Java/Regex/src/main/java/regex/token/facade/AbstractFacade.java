package regex.token.facade;

import regex.IToken;

import java.util.List;

public abstract class AbstractFacade implements IToken {
    protected List<IToken> innerTokens;

    protected AbstractFacade(List<IToken> innerTokens) {
        this.innerTokens = innerTokens;
    }

    @Override
    public boolean reset() {
        return false;
    }
}
