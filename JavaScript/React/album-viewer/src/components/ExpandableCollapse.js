import React from 'react'
import clsx from 'clsx'

import { withStyles } from '@material-ui/core/styles'
import { Collapse, Grid, Typography, IconButton } from '@material-ui/core'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

const styles = theme => ({
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }), 
    },
    expandOpen: {
        transform: 'rotate(360deg)',
    },
})

class ExpandableCollapse extends React.PureComponent {
    constructor(props) {
        super(props)
        this.state = {
            expanded: props.expanded,
        }
    }

    toggleExpand = (e) => {
        if (this.props.onToggleExpand) {
            this.setState({
                expanded: this.props.onToggleExpand(this.state.expanded),
            });
        } else {
            this.setState({
                expanded: !this.state.expanded,
            });
        }

    }

    render() {
        return (
            <>
                <Grid
                    container
                    direction="row-reverse"
                    justify="flex-start"
                    alignItems="center"
                    wrap="nowrap"
                >
                    <IconButton
                        className={clsx(this.props.classes.expand, {
                            [this.props.classes.expandOpen]: this.state.expanded,
                        })}
                        onClick={this.toggleExpand}
                        aria-expanded={this.state.expanded}
                        aria-label="Show more"
                    >
                        <ExpandMoreIcon />
                    </IconButton>
                    {this.props.leftElements}
                </Grid>
                <Collapse in={this.state.expanded} timeout="auto" unmountOnExit>
                    <div style={{ height: '100%' }}>
                        {this.props.children}
                    </div>
                </Collapse>
            </>
        );
    }
}

export default withStyles(styles)(ExpandableCollapse);