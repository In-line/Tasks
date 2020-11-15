import React from 'react';
import { ListItem, ListItemText } from '@material-ui/core';
import { FixedSizeList as List } from 'react-window'
import AutoSizer from 'react-virtualized-auto-sizer';
import Link from './Link';

import { connect } from 'react-redux'

class LinkList extends React.PureComponent {
    renderItems = ({ index, style }) => {
        let link = this.props.list[index];
        let shouldDrawEndMargin = this.props.list.length === index && index > 0;

        return (
            <div style={style} key={link ? link.id : -1}>
                {!shouldDrawEndMargin &&
                    <ListItem>
                        <ListItemText  primary={this.props.list.length - index} style={{ marginRight: '1%' }} />
                        <Link {...link} />
                    </ListItem>}
                {
                    shouldDrawEndMargin && <div style={{ marginTop: '5%' }} />
                }
            </div>
        );
    }

    render() {
        return (
            <AutoSizer defaultHeight={100} style={{ height: '100px' }}>
                {({ height, width }) => (
                    <List height={height} width={width} itemCount={(this.props.list.length ? this.props.list.length : -1) + 1} itemSize={100} >
                        {this.renderItems}
                    </List>
                )}
            </AutoSizer>
        )
    }
}

const mapStateToProps = state => ({
    list: state.links.list,
})


export default connect(mapStateToProps)(LinkList)