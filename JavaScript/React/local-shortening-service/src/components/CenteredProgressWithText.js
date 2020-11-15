import React from 'react'
import { CircularProgress, Typography, Grid } from '@material-ui/core/'

class CenteredProgressWithText extends React.Component {
    render() {
        return (
            <div style={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translateX(-50%) translateY(-50%)',
            }}>
                <Grid
                    container
                    direction="row"
                    justify="center"
                    alignItems="center"
                    wrap="nowrap"
                >
                    <Typography noWrap={true} variant="h4" color="primary">
                        {this.props.text}
                    </Typography>
                    <div style={{marginRight: '1%'}}/>
                    <CircularProgress color="primary" thickness={7.2} disableShrink={true} />

                </Grid>
            </div>
        )
    }
}

export default CenteredProgressWithText;