import React from 'react'

import {Spinner} from 'reactstrap'

class HorizontallyCenteredSpinner extends React.PureComponent {
    render() {
        return (
            <>
                <div className="text-center">
                    <div style={{ marginTop: "1rem" }}></div>
                    <Spinner color="primary" />
                </div>
            </>
        );
    }
}

export default HorizontallyCenteredSpinner;