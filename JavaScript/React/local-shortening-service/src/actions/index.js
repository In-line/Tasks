import { TOGGLE_EXPANDED, SHORTEN_URL, TEXT_CHANGED, EXPAND, CLEAR_ALL } from './actionTypes'

export const toggleExpanded = () => ({
    type: TOGGLE_EXPANDED,
    payload: {},
})

export const shortenURL = (value) => ({
    type: SHORTEN_URL,
    payload: { value },
})

export const textChanged = (value) => ({
    type: TEXT_CHANGED,
    payload: { value },
})

export const expand = () => ({
    type: EXPAND,
    payload: {},
})

export const clearAll = () => ({
    type: CLEAR_ALL,
    payload: {},
})