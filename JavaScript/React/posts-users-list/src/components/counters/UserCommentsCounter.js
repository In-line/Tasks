import React from 'react'
import {Spinner, Badge} from 'reactstrap'
import { connect } from 'react-redux'
import {RestStatus} from '../../reducers/rest'

import { getPostsByUserIdCached, getCommentsByPostIdCached } from '../../actions'


class UserActivityCounter extends React.PureComponent {
    componentDidMount() {
        this.props.syncPostsByUserId(this.props.id)
    }

    render() {
        const { postsByUserId, commentsByPostId, color } = this.props;
        const posts = postsByUserId[this.props.id];

        if (!posts || posts.status !== RestStatus.Loaded) {
            return <Spinner color={color} size="sm" />;
        } else {
            let totalCount = 0;
            let failedToLoadSomething = false;

            for (let post of posts.payload) {
                let comments = commentsByPostId[post.id];
                if (!comments || comments.status !== RestStatus.Loaded) {
                    failedToLoadSomething = true;
                    
                    if (!comments || comments.status !== RestStatus.Loading) {
                        this.props.syncCommentsByPostId(post.id);
                    }
                } else {
                    totalCount += comments.payload.length;
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
const mapStateToProps = state => ({
    postsByUserId: state.rest.postsByUserId ? state.rest.postsByUserId : {},
    commentsByPostId: state.rest.commentsByPostId ? state.rest.commentsByPostId : {},
})

const mapDispatchToProps = {
    syncPostsByUserId: getPostsByUserIdCached,
    syncCommentsByPostId: getCommentsByPostIdCached
}
export default connect(mapStateToProps, mapDispatchToProps)(UserActivityCounter);