import LoadableList from './LoadableList'
import React from 'react'
import { connect } from 'react-redux'
import { restActions } from '../actions'
import PhotoList from './PhotoList'
import ExpandableCollapse from './ExpandableCollapse'

import { Typography, Grid, CircularProgress } from '@material-ui/core'

class AlbumList extends React.Component {
    constructor(props) {
        super(props)
        this.state = { expanded: null, }
    }

    componentDidMount() {
        this.props.sync({ id: this.props.userId });
    }

    onToggleExpand = (wasExpanded, index) => {
        if (wasExpanded) {
            this.setState({ expanded: null });
        } else {
            this.setState({ expanded: index });
        }

        return !wasExpanded || this.state.expanded === index;
    }

    renderItem = (index, item) => (
        <div style={{ height: '100%', width: '100%' }}>
            <ExpandableCollapse
                onToggleExpand={(isExpanded) => this.onToggleExpand(isExpanded, index)}
                expanded={this.state.expanded === index}
                leftElements={
                    <Typography
                        variant="h6"
                        color="secondary"
                        align="center"
                        noWrap
                        style={{ marginLeft: '1%' }}>
                        {item.title}
                    </Typography>
                }
            >
                <PhotoList albumId={item.id} />
            </ExpandableCollapse>
        </div>
    )

    render() {
        console.log(this.state.expanded);
        
        if (this.props.items.loading || !this.props.items.data) {
            return (
                <Grid container justify="center">
                    <CircularProgress color="primary" disableShrink={true} />
                </Grid>
            )
        } else {
            return (<LoadableList {...this.props} items={
                {
                    ...this.props.items,
                    data: this.props.items.data
                }
            } renderItem={(index, item) => this.renderItem(index, item)} />)
        }
    }
}

const mapStateToProps = state => ({
    items: state.albums
})

const mapDispatchToProps = {
    sync: restActions.albums
}

export default connect(mapStateToProps, mapDispatchToProps)(AlbumList);