import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

import App from './components/App';
import Redirecter from './components/Redirecter';
import CenteredProgressWithText from './components/CenteredProgressWithText';
import rootReducer from './reducers'
import * as serviceWorker from './serviceWorker';
import './index.css'

import { createStore } from 'redux'
import { persistStore, persistReducer } from 'redux-persist'
import { PersistGate } from 'redux-persist/integration/react'
import autoMergeLevel1 from 'redux-persist/lib/stateReconciler/autoMergeLevel1'
import createCompressor from 'redux-persist-transform-compress'
import localforage from 'localforage'

localforage.config({
    driver: localforage.LOCALSTORAGE,
    name: 'local-shorteting-service',
    version: 1.0,
    storeName: 'state', // Should be alphanumeric, with underscores.
    description: 'App state'
});

const persistConfig = {
    key: 'root',
    storage: localforage,
    stateReconciler: autoMergeLevel1,
    transforms: [createCompressor()]
}

const persistedReducer = persistReducer(persistConfig, rootReducer)
const store = createStore(persistedReducer, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());
export const persistor = persistStore(store)

ReactDOM.render(
    <Provider store={store}>
        <Router>
            <PersistGate loading={
            <CenteredProgressWithText text="Unpacking..."/>
            } persistor={persistor}>
                <Switch>
                    <Route exact path="/" component={App} />
                    <Route path="/" component={Redirecter} />
                </Switch>
            </PersistGate>
        </Router>
    </Provider>,
    document.getElementById('root')
);

serviceWorker.register();
