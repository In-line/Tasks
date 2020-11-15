import React from 'react'
import { withRouter, Redirect as RouteRedirect } from 'react-router-dom'
import { connect } from 'react-redux'
import binarySearch from 'binarysearch'
import ExternalRedirect from './ExternalRedirect'
import Alert from './Alert'


class Redirecter extends React.Component {
    render() {
        let idToSearch = parseInt(this.props.location.pathname.substr(1), 36)

        let index = binarySearch(this.props.links, idToSearch,
            (value, find) => {
                return find - value.id
            })
        if (index === -1 || !Number.isInteger(idToSearch)) {
            return (
                <Alert title="404" text="Short link not found">
                    <RouteRedirect to="/" push={true}/>
                </Alert>
                );
        } else {
            return <ExternalRedirect to={this.props.links[index].original} />
        }
    }
}

const mapStateToProps = state => ({
    links: state.links.list
})

export default connect(mapStateToProps)(withRouter(Redirecter));