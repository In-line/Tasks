import React from 'react'
import {Spinner, Badge} from 'reactstrap'
import { connect } from 'react-redux'
import {RestStatus} from '../../reducers/rest'


class ActivityCounter extends React.PureComponent {
    componentDidMount() {
        this.props.sync(this.props.id)
    }

    render() {
        const { items, color } = this.props;
        const item = items[this.props.id]
        if (!item || item.status !== RestStatus.Loaded) {
            return <Spinner color={color} size="sm" />;
        } else {
            return <Badge color={color}>{item.payload.length}</Badge>
        }
    }
}

export default function(field, action) {
    const mapStateToProps = state => ({
        items: state.rest[field] ? state.rest[field] : {}
    })
    
    const mapDispatchToProps = {
        sync: action
    }

    return connect(mapStateToProps, mapDispatchToProps)(ActivityCounter);
}