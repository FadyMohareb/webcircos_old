/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var converter = new Showdown.converter();

var HomePage = React.createClass({
  displayName: "HomePage",
  getInitialState: function getInitialState() {
    return { view: { showModal: false, showRegModal: false, showResetModal: false,
            showChangeModal: false, showUploadModal: false }};
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
                      this.state.view.showModal ? React.createElement(LoginModal, { handleHideModal: this.handleHideModal,
                          handleShowRegModal: this.handleShowRegModal, handleShowResetModal: this.handleShowResetModal}) : null,
                      this.state.view.showRegModal ? React.createElement(RegistrationModal, { handleHideRegModal: this.handleHideRegModal }) : null,
                      this.state.view.showResetModal ? React.createElement(ResetPswdModal, { handleHideResetModal: this.handleHideResetModal }) : null)
    );
  }
});

var renderHomePage = function () {
    React.render(React.createElement(HomePage, null), document.getElementById('upperLeftContainer'));
};
