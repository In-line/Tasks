import React from 'react';
import { Grid, Button } from '@material-ui/core';

import { Redirect } from 'react-router-dom';

class Link extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = { redirect: false }
    }

    redirect = () => {
        this.setState({ redirect: true });
    }

    render() {
        return (
            <>
                <Grid
                    container
                    direction="column"
                    justify="space-evenly"
                    alignItems="stretch"
                    wrap="nowrap"
                >
                    <Button variant="contained" color="primary" onClick={this.redirect}>
                        {this.props.short}
                    </Button>
                    <Button variant="contained" color="secondary" onClick={this.redirect}>
                        {this.props.original}  
                    </Button>
                </Grid>
                {this.state.redirect && <Redirect to={this.props.id.toString(36)} push={true}/>}
            </>
        )
    }
}

export default Link;