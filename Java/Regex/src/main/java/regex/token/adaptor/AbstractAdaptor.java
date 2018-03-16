package regex.token.adaptor;

import regex.IToken;

public abstract class AbstractAdaptor implements IToken {
    protected IToken innerToken;

    protected AbstractAdaptor(IToken innerToken) {
        this.innerToken = innerToken;
    }
}
