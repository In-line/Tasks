import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import { NavigationBar, Users, Posts } from "./components";
import * as serviceWorker from "./serviceWorker";
import rootReducer from "./reducers";

import { apiMiddleware } from "redux-api-middleware";
import thunkMiddleware from "redux-thunk";
import { Provider } from "react-redux";
import { createStore, applyMiddleware } from "redux";
import { BrowserRouter as Router, Route, Redirect } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { composeWithDevTools } from "redux-devtools-extension";
import { cacheEnhancer } from "redux-cache";

const store = createStore(
    rootReducer,
    composeWithDevTools({ trace: false, traceLimit: 25 })(
        applyMiddleware(thunkMiddleware, apiMiddleware),
        cacheEnhancer({ log: true })
    )
);

function RedirectToPosts(props) {
    return <Redirect to="/posts" />;
}

ReactDOM.render(
    <Provider store={store}>
        <Router>
            <Route path="/" exact component={RedirectToPosts} />
            <Route path="/" component={NavigationBar} />
            <Route path="/users" exact component={Users} />
            <Route path="/posts" exact component={Posts} />
        </Router>
    </Provider>,
    document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.register();
