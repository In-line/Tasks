import React from 'react';

import { CircularProgress, ListItem, Grid, Card, List } from '@material-ui/core'



class LoadableList extends React.Component {
    render() {
        const items = this.props.items;
        if (items.loading) {
            return (
                <Grid container justify="center">
                    <CircularProgress color="primary" disableShrink={true} />
                </Grid>
            )
        } else {
            let toRender = [];

            for (let i = 0; i < items.data.length; i++) {
                const item = items.data[i];
                toRender.push(
                    <ListItem button divider style={{ height: '100%' }} key={item.id}>
                        {this.props.renderItem(i, items.data[i])}
                    </ListItem>
                )
            }

            return (
                <Card style={{ height: 'calc(100% - 16px)', margin: '8px', boxSizing: 'content-box' }}>
                    <List style={{maxHeight: '100%', overflowY: 'auto'}}>
                        {toRender}
                    </List>
                </Card>
            )
        }
    }
}

export default LoadableList;