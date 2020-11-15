import React from 'react';
import LinkList from './LinkList';

import { connect } from 'react-redux'

import { withStyles } from '@material-ui/core/styles';

import { Card, Collapse, IconButton, TextField, Grid, Typography } from '@material-ui/core'

import clsx from 'clsx';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

import { toggleExpanded, shortenURL, textChanged, expand, clearAll } from '../actions'

const styles = theme => ({
  expand: {
    transform: 'rotate(0deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
      duration: theme.transitions.duration.shortest,
    }),
  },
  expandOpen: {
    transform: 'rotate(180deg)',
  },
})

class App extends React.Component {
  render() {
    return (
      <>
        <Card style={{ maxHeight: 'inherit' }}>

          <Grid
            container
            direction="row-reverse"
            justify="flex-start"
            alignItems="flex-start"
            wrap="nowrap"
          >
            <IconButton
              className={clsx(this.props.classes.expand, {
                [this.props.classes.expandOpen]: this.props.expanded,
              })}
              onClick={this.props.toggleExpanded}
              aria-expanded={this.props.expand}
              aria-label="Show more"
            >
              <ExpandMoreIcon />
            </IconButton>
            <TextField
              label="Write URL to shorten it"
              value={this.props.lastTextInField}
              style={{ margin: 8 }}
              placeholder="https://example.com"
              fullWidth
              margin="normal"
              onKeyPress={this.handleKeyPress}
              onChange={this.handleTextChanged}
              InputLabelProps={{
                shrink: true,
              }}
            />
          </Grid>
          <Collapse in={this.props.expanded} timeout="auto" unmountOnExit style={{ maxHeight: 'inherit' }}>
            <div style={{ height: '100vh', maxHeight: 'inherit' }}>
              <LinkList />
            </div>
          </Collapse>
          <div style={{
            textAlign: 'center',
            position: 'absolute',
            top: '50%',
            left: '50%',
            zIndex: -1000,
            transform: 'translateX(-50%) translateY(-50%)',
            opacity: this.props.expanded ? '0' : '1',
            visibility: this.props.expanded ? 'hidden' : 'visible',
            transition: this.props.expanded ? 'visibility 0s 2s, opacity 2s linear' : 'opacity 2s linear'
          }}>

            <Typography noWrap={true} variant="h3" color="primary">
              Write URL to shorten it
            </Typography>

          </div>
        </Card>
      </>
    );
  }

  handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      this.props.shortenURL(e.target.value);
      if (!this.props.expanded) {
        this.props.expand();
      }
      e.preventDefault();
    }
    this.props.textChanged(e.target.value);
  }

  handleTextChanged = (e) => {
    this.props.textChanged(e.target.value);
  }
}

const mapStateToProps = state => {
  return { expanded: state.expanded, listLength: state.links.list.length, lastTextInField: state.lastTextInField };
}

const mapDispatchToProps = {
  toggleExpanded,
  shortenURL,
  textChanged,
  expand,
  clearAll
}

export default withStyles(styles)(connect(mapStateToProps, mapDispatchToProps)(App));