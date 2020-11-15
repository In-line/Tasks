import React from 'react';
import { GridList, GridListTile, Grid, CircularProgress } from '@material-ui/core';

import { connect } from 'react-redux'

import { restActions } from '../actions'

class PhotoList extends React.Component {

    componentDidMount() {
        this.props.sync({ id: this.props.albumId });
    }

    render() {
        let photos = this.props.photos;

        if (photos.loading || !photos.data) {
            return (
                <Grid container justify="center">
                    <CircularProgress color="primary" disableShrink={true} />
                </Grid>
            )
        } else {
            return (
                <GridList cellHeight={160} cols={3}>
                    {photos.data.filter(photo => photo.albumId === this.props.albumId).map(photo => (
                        <GridListTile key={photo.id} cols={1}>
                            <img src={photo.thumbnailUrl} alt={photo.title} />
                        </GridListTile>
                    ))}
                </GridList>
            )
        }
    }
}

const mapStateToProps = (state) => ({
    photos: state.photos,
})

const mapDispatchToProps = {
    sync: restActions.photos,
}

export default connect(mapStateToProps, mapDispatchToProps)(PhotoList);