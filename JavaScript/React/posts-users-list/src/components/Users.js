import React from 'react'
import { connect } from 'react-redux'
import { Table } from 'reactstrap'

import HorizontallyCenteredSpinner from './HorizontallyCenteredSpinner'
import {UserCommentsCounter, UserPostCounter, UserAlbumsCounter, UserPhotosCounter} from './counters'


import { RestStatus } from '../reducers/rest'
import { getUsersCached } from '../actions'



class Users extends React.PureComponent {
    componentDidMount() {
        this.props.sync();
    }

    render() {
        const { users } = this.props;

        if (users.status !== RestStatus.Loaded) {
            return <HorizontallyCenteredSpinner />;
        } else {
            let index = 1;
            return (
                <Table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Posts</th>
                            <th>Comments</th>
                            <th>Albums</th>
                            <th>Photos</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users
                            .payload
                            .map(user =>
                                <tr key={user.id}>
                                    <th scope="row">{index++}</th>
                                    <td>{user.name}</td>
                                    <td><UserPostCounter color="info" id={user.id} /></td>
                                    <td><UserCommentsCounter color="info" id={user.id} /></td>
                                    <td><UserAlbumsCounter color="info" id={user.id} /></td>
                                    <td><UserPhotosCounter color="info" id={user.id} /></td>

                                </tr>
                            )}
                    </tbody>
                </Table>
            )
        }
    }
}

const mapStateToProps = state => ({
    users: state.rest.users ? state.rest.users : {}
})

const mapDispatchToProps = {
    sync: getUsersCached
}

export default connect(mapStateToProps, mapDispatchToProps)(Users);