import React from 'react'
import { connect } from 'react-redux'
import { ListGroup, ListGroupItem, Card, CardBody, CardTitle, CardText, CardSubtitle } from 'reactstrap'

import HorizontallyCenteredSpinner from './HorizontallyCenteredSpinner'
import UserName from './UserName'
import { getPostsCached } from '../actions'
import { RestStatus } from '../reducers/rest'

import {CommmentsCounter} from './counters'


class Posts extends React.Component {
    componentDidMount() {
        this.props.sync();
    }

    render() {
        const { posts } = this.props;
        if (posts.status !== RestStatus.Loaded) {
            return <HorizontallyCenteredSpinner />;
        } else {
            return (
                <div style={{overflowY: 'auto', maxHeight: '90vh'}}>
                <ListGroup>
                    {posts.payload.map(post =>
                        <ListGroupItem key={post.id}>
                            <Card>
                                <CardBody>
                                    <CardTitle><b>{post.title}</b> <CommmentsCounter id={post.id} /> </CardTitle>
                                    <CardSubtitle><UserName userId={post.userId}/></CardSubtitle>
                                    <CardText>{post.body}</CardText>
                                </CardBody>
                            </Card>
                        </ListGroupItem>
                    )}
                    </ListGroup>
                </div>
            )
        }
    }
}

const mapStateToProps = state => ({
    posts: state.rest.posts ? state.rest.posts : {}
})

const mapDispatchToProps = {
    sync: getPostsCached
}

export default connect(mapStateToProps, mapDispatchToProps)(Posts);