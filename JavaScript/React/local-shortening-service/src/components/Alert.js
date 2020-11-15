import React from 'react'

import {Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button} from '@material-ui/core'

class Alert extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = { open: true };
    }

    renderDialog() {
        return (
            <div>
              <Dialog
                open={this.state.open}
                onClose={(e) => this.setState({open: false})}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                fullWidth
              >
                <DialogTitle id="alert-dialog-title">{this.props.title}</DialogTitle>
                <DialogContent>
                  <DialogContentText id="alert-dialog-description">
                    {this.props.text}
                  </DialogContentText>
                </DialogContent>
                <DialogActions>
                  <Button onClick={(e) => this.setState({open: false})} color="primary" autoFocus fullWidth>
                    Close
                  </Button>
                </DialogActions>
              </Dialog>
            </div>
          );
    }

    render() {
        if (this.state.open) {
            return this.renderDialog();
        } else {
            return this.props.children;
        }
    }
}

export default Alert;