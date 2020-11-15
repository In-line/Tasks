import React from 'react'
import * as URI from "uri-js";
import {persistor} from '../index'

class ExternalRedirect extends React.Component {
    render() {
        const uri = URI.parse(this.props.to)
        const isHTTP = uri.scheme == null || uri.scheme === 'http';
        persistor.flush();
        window.location.replace((isHTTP ? 'http://' : '') + URI.serialize(uri));
        return null;
    }
}

export default ExternalRedirect;