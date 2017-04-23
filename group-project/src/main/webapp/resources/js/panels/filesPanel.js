/* global React, FilesTabsComponent, Showdown, UploadModal, ImportModal */

var converter = new Showdown.converter();

var FilesPanel = React.createClass({displayName: "FilesPanel",
    getInitialState: function getInitialState() {
        return {view: {showUploadModal: false, showImportModal: false, showRemoveModal: false}};
    },
    componentWillReceiveProps: function (newProperties)
    {
        this.props.userStatus = newProperties.userStatus;
    },
    handleShowUploadModal: function handleShowUploadModal() {
        this.setState({view: {showUploadModal: true}});
    },
    handleHideUploadModal: function handleHideUploadModal() {
        this.setState({view: {showUploadModal: false}});
        $(".modal-backdrop.in").remove();
    },
    handleShowImportModal: function handleShowImportModal() {
        this.setState({view: {showImportModal: true}});
    },
    handleHideImportModal: function handleHideImportModal() {
        this.setState({view: {showImportModal: false}});
        $(".modal-backdrop.in").remove();
    },
    handleShowRemoveModal: function handleShowRemoveModal() {
        this.setState({view: {showRemoveModal: true}});
    },
    handleHideRemoveModal: function handleHideRemoveModal() {
        this.setState({view: {showRemoveModal: false}});
        $(".modal-backdrop.in").remove();
    },
    render: function () {

        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null, "Files  "),
                        React.createElement('btn-group', {role: 'group'},
                                React.createElement('button', {id: 'uploadModalBtn', type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowUploadModal},
                                        React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'})),
                                React.createElement('button', {id: "importModalBtn", type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowImportModal},
                                        React.createElement('span', {className: 'glyphicon glyphicon-import', 'aria-hidden': 'true'})),
                                React.createElement('button', {id: "removeModalBtn", type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowRemoveModal},
                                        React.createElement('span', {className: 'glyphicon glyphicon-trash', 'aria-hidden': 'true'}))
                                )),
                React.createElement('div', {className: "panel-body", id: 'filesTabsContainer'},
                        React.createElement(FilesTabsComponent, {projectName: this.props.projectName})),
                this.state.view.showUploadModal ? React.createElement(UploadModal, {handleHideUploadModal: this.handleHideUploadModal, projectName: this.props.projectName}) : null,
                this.state.view.showImportModal ? React.createElement(ImportModalTest, {handleHideImportModal: this.handleHideImportModal, projectName: this.props.projectName}) : null,
                this.state.view.showRemoveModal ? React.createElement(RemoveModal, {handleHideRemoveModal: this.handleHideRemoveModal, projectName: this.props.projectName}) : null));
        ;
    }
});

var renderFilesPanel = function (isLogged) {
    console.log('IsLogged ' + isLogged);

    var logged = null;

    if (isLogged !== null && isLogged !== "")
        logged = true;
    else
        logged = false;

    React.render(
            React.createElement(FilesPanel, {userStatus: logged}),
            document.getElementById("filesContainer")
            );

    if (logged) {
        $('#importModalBtn').attr('disabled', false);
    } else {
        $('#importModalBtn').attr('disabled', true);
    }
};

