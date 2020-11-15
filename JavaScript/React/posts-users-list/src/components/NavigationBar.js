import { Nav, NavItem, NavLink as RSNavLink } from 'reactstrap';
import React from 'react';

import { withRouter, NavLink as RRNavLink } from 'react-router-dom';

function NavLink(props) {
    return (
        <RSNavLink
            tag={RRNavLink}
            exact
            to={props.href}
            activeClassName="active"
            active={props.href === props.location.pathname}>
            {props.children}
        </RSNavLink>
    )
}

class NavigationBar extends React.PureComponent {
    constructor(props) {
        super(props)
        this.state = {marginBottom: '1px'}
    }

    setMarginBottom = element => {
        if (element) {
            this.setState({marginBottom: element.clientHeight + 'px'})
        }
    }


    render() {
        const {location} = this.props;
        return (
            <>
            <div className="fixed-top" ref={this.setMarginBottom}>
            <Nav tabs ref={this.setMarginBottom}>
                <NavItem>
                    <NavLink href="/posts" location={location}>Posts</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink href="/users" location={location}>Users</NavLink>
                </NavItem>
                </Nav>
            </div>
                <div style={{marginBottom: this.state.marginBottom}}/>
            </>
        );
    }
}

export default withRouter(props => <NavigationBar {...props} />)