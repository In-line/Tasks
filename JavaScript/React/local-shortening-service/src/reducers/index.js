import { combineReducers } from 'redux'
import { TOGGLE_EXPANDED, SHORTEN_URL, TEXT_CHANGED, EXPAND, CLEAR_ALL } from '../actions/actionTypes'

const linksReducer = (state = { nextId: 0, list: [] }, action) => {
    switch (action.type) {
        case SHORTEN_URL:
            return {
                ...state,
                nextId: state.nextId + 1,
                list: [
                    {
                        original: action.payload.value,
                        short: /*process.env.PUBLIC_URL*/ 'http://127.0.0.1:3000/' + state.nextId.toString(36),
                        id: state.nextId
                    },
                    ...state.list
                ]
            };
        case CLEAR_ALL:
            return linksReducer(undefined, {type: null});
        default:
            return state;
    }
}

const expandedReducer = (state = false, action) => {
    switch (action.type) {
        case TOGGLE_EXPANDED:
            return !state;
        case EXPAND:
            return true;
        case CLEAR_ALL:
            return expandedReducer(undefined, {type: null});
        default:
            return state;
    }
}

const lastTextInFieldReducer = (state = "", action) => {
    switch (action.type) {
        case TEXT_CHANGED:
            return action.payload.value;
        case CLEAR_ALL:
            return lastTextInFieldReducer(undefined, {type: null});
        default:
            return state;
    }
}

export default combineReducers({ links: linksReducer, expanded: expandedReducer, lastTextInField: lastTextInFieldReducer });