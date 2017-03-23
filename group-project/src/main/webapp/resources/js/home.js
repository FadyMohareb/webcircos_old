/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var converter = new Showdown.converter();

var App = React.createClass({
  displayName: "App",
  getInitialState: function getInitialState() {
    return { view: { showModal: false, showRegModal: false, showResetModal: false, showChangeModal: false, showUploadModal: false } };
  },
  handleHideModal: function handleHideModal() {
    this.setState({ view: { showModal: false } });
    $( ".modal-backdrop.in" ).remove();
  },
  handleShowModal: function handleShowModal() {
    this.setState({ view: { showModal: true } });
  },
  handleShowRegModal: function handleShowRegModal() {
    this.setState({ view: { showRegModal: true } });
  },
  handleHideRegModal: function handleHideRegModal() {
    this.setState({ view: { showRegModal: false } });
    $( ".modal-backdrop.in" ).remove();
  },
  handleShowResetModal: function handleShowResetModal() {
    this.setState({ view: { showResetModal: true } });
  },
  handleHideResetModal: function handleHideResetModal() {
    this.setState({ view: { showResetModal: false } });
    $( ".modal-backdrop.in" ).remove();
  },
    handleShowChangeModal: function handleShowChangeModal() {
    this.setState({ view: { showChangeModal: true } });
  },
  handleHideChangeModal: function handleHideChangeModal() {
    this.setState({ view: { showChangeModal: false } });
    $( ".modal-backdrop.in" ).remove();
  },
  
  render: function render() {
    return (
                React.createElement("div", {className: "pull-left"},
                    React.createElement("button", { className: 'btn btn-primary', onClick: this.handleShowModal}, "Sign in"),
                      this.state.view.showModal ? React.createElement(SigninForm, { handleHideModal: this.handleHideModal,
                          handleShowRegModal: this.handleShowRegModal, handleShowResetModal: this.handleShowResetModal}) : null,
                      this.state.view.showRegModal ? React.createElement(SignupForm, { handleHideRegModal: this.handleHideRegModal }) : null,
                      this.state.view.showResetModal ? React.createElement(ResetPswdForm, { handleHideResetModal: this.handleHideResetModal }) : null)
    );
  }
});

var renderJoinUS = function () {
    React.render(React.createElement(App, null), document.getElementById('upperLeftContainer'));
};

var ProjectsAnonym = React.createClass({
  displayName: "projAn",
  render: function () {
        return (React.createElement('div', {className: "panel panel-info"},
                    React.createElement('div', {className: "panel-heading"}, "Projects"),
                    React.createElement('div', {className: "panel-body"}, "Sign in to access your projects")
            )
          );
    }
});

var renderProjAn = function () {
    React.render(React.createElement(ProjectsAnonym), document.getElementById('projAnContainer'));
};
