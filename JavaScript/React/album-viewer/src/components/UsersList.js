import LoadableList from './LoadableList'
import React from 'react'
import { connect } from 'react-redux'
import { restActions } from '../actions'
import AlbumList from './AlbumList'
import ExpandableCollapse from './ExpandableCollapse'

import { ListItemText, Typography } from '@material-ui/core'

class UsersList extends React.PureComponent {

    componentDidMount() {
        this.props.sync();
    }

    renderItem = (index, user) => (
        <div style={{height: '100%', width: '100%'}}>
            <ExpandableCollapse
                leftElements={
                    <Typography
                        variant="h6"
                        color="secondary"
                        align="center"
                        noWrap
                        style={{ marginLeft: '1%' }}>{user.name}</Typography>
                }
                >
                <AlbumList userId={user.id} />
            </ExpandableCollapse>
        </div>
    )

    render() {
        return <LoadableList {...this.props} items={
            {
                ...this.props.items,
                data: this.props.items.data.slice(5)
            }
        } renderItem={this.renderItem}
          onClick={this.onClickListener}/>
    }
}

const mapStateToProps = state => ({
    items: state.users
})

const mapDispatchToProps = {
    sync: restActions.users.sync
}

export default connect(mapStateToProps, mapDispatchToProps)(UsersList);