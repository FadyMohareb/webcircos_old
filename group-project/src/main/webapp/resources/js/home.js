/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var converter = new Showdown.converter();

var HomePage = React.createClass({
    displayName: "HomePage",
    getInitialState: function getInitialState() {
        return {view: {showModal: false, showRegModal: false, showResetModal: false,
                showChangeModal: false, showUploadModal: false}};
    },
    handleHideModal: function handleHideModal() {
        this.setState({view: {showModal: false}});
        $(".modal-backdrop.in").remove();
    },
    handleShowModal: function handleShowModal() {
        this.setState({view: {showModal: true}});
    },
    handleShowRegModal: function handleShowRegModal() {
        this.setState({view: {showRegModal: true}});
    },
    handleHideRegModal: function handleHideRegModal() {
        this.setState({view: {showRegModal: false}});
        $(".modal-backdrop.in").remove();
    },
    handleShowResetModal: function handleShowResetModal() {
        this.setState({view: {showResetModal: true}});
    },
    handleHideResetModal: function handleHideResetModal() {
        this.setState({view: {showResetModal: false}});
        $(".modal-backdrop.in").remove();
    },
    handleShowChangeModal: function handleShowChangeModal() {
        this.setState({view: {showChangeModal: true}});
    },
    handleHideChangeModal: function handleHideChangeModal() {
        this.setState({view: {showChangeModal: false}});
        $(".modal-backdrop.in").remove();
    },
    
//    checkAuth: function () {
//            var isLogged = null;
//            $.ajax({
//                'async': false,
//                'global': false,
//                'url': '/isLogged',
//                'success': function (resp) {
//                    isLogged = (resp === "1");
//                }
//            });
//            return isLogged;
//    },

    render: function render() {
        return (React.createElement("div", {className: "container", padding: '0px 0px 0px 0px'},
                React.createElement("div", {className: "row"},
                        React.createElement("div", {className: "col-lg-6", style: {float: "left"}},
                                React.createElement("button", {className: 'btn btn-primary', onClick: this.handleShowModal}, "Sign in")),
                        React.createElement("div", {className: "col-lg-6", style: {float: "right"}},
                                React.createElement(AccountDropdown))),
                React.createElement("div", {className: "row"},
                        React.createElement("div", {className: "col-lg-3"},
                                React.createElement("div", {className: "row"},
                                        React.createElement(ProjectsPanel)),
                                React.createElement("div", {className: "row"},
                                        React.createElement(FilesPanel))),
                        React.createElement("div", {className: "col-lg-6"},
                                React.createElement(CircosPanel)),
                        React.createElement("div", {className: "col-lg-2"},
                                React.createElement(ViewPanel))),
                this.state.view.showModal ? React.createElement(LoginModal, {handleHideModal: this.handleHideModal,
                    handleShowRegModal: this.handleShowRegModal, handleShowResetModal: this.handleShowResetModal}) : null,
                this.state.view.showRegModal ? React.createElement(RegistrationModal, {handleHideRegModal: this.handleHideRegModal}) : null,
                this.state.view.showResetModal ? React.createElement(ResetPswdModal, {handleHideResetModal: this.handleHideResetModal}) : null)
                );
    }
});

var renderHomePage = function () {
    React.render(React.createElement(HomePage, null), document.getElementById('upperLeftContainer'));
};
