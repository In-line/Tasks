
import { combineReducers } from "redux"
import restReducer from './rest'

export default combineReducers({ rest: restReducer });
