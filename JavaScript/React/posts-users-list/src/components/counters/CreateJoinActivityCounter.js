import React from 'react'
import {Spinner, Badge} from 'reactstrap'
import { connect } from 'react-redux'
import {RestStatus} from '../../reducers/rest'

import { getPostsByUserIdCached, getCommentsByPostIdCached } from '../../actions'


class UserJoinActivityCounter extends React.PureComponent {
    componentDidMount() {
        this.props.syncUserItemsByUserId(this.props.id)
    }

    render() {
        const { itemsByUserId, itemsByAnotherItemId, color } = this.props;
        const userItems = itemsByUserId[this.props.id];

        if (!userItems || userItems.status !== RestStatus.Loaded) {
            return <Spinner color={color} size="sm" />;
        } else {
            let totalCount = 0;
            let failedToLoadSomething = false;

            for (let userItem of userItems.payload) {
                let items = itemsByAnotherItemId[userItem.id];
                if (!items || items.status !== RestStatus.Loaded) {
                    failedToLoadSomething = true;
                    
                    if (!items || items.status !== RestStatus.Loading) {
                        this.props.syncItemsByAnotherItemId(userItem.id);
                    }
                } else {
                    totalCount += items.payload.length;
                }
            }

            if (failedToLoadSomething) {
                return <Spinner color={color} size="sm" />;
            } else {
                return <Badge color={color}>{totalCount}</Badge>
            }
        }
    }
}

const CreateJoinActivityCounter = (fieldByUserId, syncUserItemsByUserId, fieldByAnotherItemId, syncItemsByAnotherItemId) => {
    const mapStateToProps = state => ({
        itemsByUserId: state.rest[fieldByUserId] ? state.rest[fieldByUserId] : {},
        itemsByAnotherItemId: state.rest[fieldByAnotherItemId] ? state.rest[fieldByAnotherItemId] : {},
    })
    
    const mapDispatchToProps = {
        syncUserItemsByUserId,
        syncItemsByAnotherItemId
    }

    return connect(mapStateToProps, mapDispatchToProps)(UserJoinActivityCounter);
}

export default CreateJoinActivityCounter;