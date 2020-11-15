import { DEFAULT_KEY, generateCacheTTL } from "redux-cache";
import { restTriple, restItemTriple } from "../actions";
import { handleActions } from "redux-actions";

import _ from "lodash";

export const RestStatus = Object.freeze({
    Loading: 0,
    Loaded: 1,
    Error: 2
});

export const restStatusTriple = Object.values(RestStatus).sort((a, b) => a - b);

const createNewState = (state, action, status) => {
    return {
        ...state,
        [action.meta.params.key]: {
            status,
            payload: action.payload,
            [DEFAULT_KEY]: generateCacheTTL()
        }
    };
};

const createItemNewState = (state, action, status) => {
    let baseState =
        [action.meta.params.key] in state
            ? state
            : { ...state, [action.meta.params.key]: {} };
    return {
        ...baseState,
        [action.meta.params.key]: {
            ...baseState[action.meta.params.key],
            [action.meta.params.id]: {
                status,
                payload: action.payload,
                [DEFAULT_KEY]: generateCacheTTL()
            }
        }
    };
};

export default handleActions(
    new Map(
        _.flatten(
            _.zip(restTriple, restItemTriple, restStatusTriple).map(
                ([type, itemType, status]) => [
                    [
                        type,
                        (state, action) => createNewState(state, action, status)
                    ],
                    [
                        itemType,
                        (state, action) =>
                            createItemNewState(state, action, status)
                    ]
                ]
            )
        )
    ),
    {}
);
