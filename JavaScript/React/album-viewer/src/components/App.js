import React from 'react';
import ExpandableCollapse from './ExpandableCollapse'
import { Grid, Typography } from '@material-ui/core'

import UsersList from './UsersList'

function App() {
  return (
    <>
      <Grid
        container
        direction="column"
        justify="flex-start"
        alignItems="stretch"
        style={{ height: '100%' }}
        wrap="nowrap"
      >
        <ExpandableCollapse
          leftElements={
            <Typography
              variant="h6"
              color="primary"
              align="center"
              style={{ marginLeft: '1%' }}>List of users and their albums</Typography>
          }>
          <UsersList/>
        </ExpandableCollapse>


      </Grid>
    </>
  );
}

export default App;
