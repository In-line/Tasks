import React from 'react'
import { Spinner, Badge } from 'reactstrap'
import { connect } from 'react-redux'

import { getUsersByIdCached } from '../actions'
import { RestStatus } from '../reducers/rest'

class UserName extends React.Component {
    componentDidMount() {
        this.props.sync(this.props.userId);
    }

    render() {
        const { items, color } = this.props;
        const item = items[this.props.userId];

        if (!item || item.status !== RestStatus.Loaded) {
            return <Spinner color={color} size="sm" />;
        } else {
            return <>{item.payload.name + (items.length > 1 ? '...' : '')}</>
        }
    }
}
const mapStateToProps = state => ({
    items: state.rest.usersById ? state.rest.usersById : {}
})

const mapDispatchToProps = {
    sync: getUsersByIdCached
}

export default connect(mapStateToProps, mapDispatchToProps)(UserName);