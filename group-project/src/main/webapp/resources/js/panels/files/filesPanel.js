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

        return (React.createElement('div', null ,
                React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null, "Files  "),
                        React.createElement('btn-group', {role: 'group', style: {float: 'right'}},
                                React.createElement('button', {id: 'uploadModalBtn', type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowUploadModal},
                                        React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'})),
                                React.createElement('button', {id: "importModalBtn", type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowImportModal},
                                        React.createElement('span', {className: 'glyphicon glyphicon-import', 'aria-hidden': 'true'})),
                                React.createElement('button', {id: "removeModalBtn", type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowRemoveModal},
                                        React.createElement('span', {className: 'glyphicon glyphicon-trash', 'aria-hidden': 'true'}))
                                )),
                React.createElement('div', {className: "panel-body", id: 'filesTabsContainer'},
                        React.createElement("ul", {className: "nav nav-pills"},
                                React.createElement("li", {className: "active"}, React.createElement("a", {'data-target': "#overview", 'data-toggle': "tab"}, React.createElement('strong', null, "Overview"))),
                                React.createElement("li", {className: ""}, React.createElement("a", {'data-target': "#bsa", 'data-toggle': "tab"}, React.createElement('strong', null, "BSA")))),
                        React.createElement("div", {className: "panel panel-primary"},
                                React.createElement("div", {className: "tab-content"},
                                React.createElement("br"),
                                React.createElement("div", {className: "tab-pane active", id: "overview"},
                                        React.createElement(FilesOverviewPanel, { projectName: this.props.projectName})),
                                        React.createElement("div", {className: "tab-pane", id: "bsa"},
                                        React.createElement(FilesBSATab, {projectName: this.props.projectName}))),
                                React.createElement("br")
                                )),
                this.state.view.showUploadModal ? React.createElement(UploadModal, {handleHideUploadModal: this.handleHideUploadModal, projectName: this.props.projectName}) : null,
                this.state.view.showImportModal ? React.createElement(ImportModalTest, {handleHideImportModal: this.handleHideImportModal, projectName: this.props.projectName}) : null,
                this.state.view.showRemoveModal ? React.createElement(RemoveModal, {handleHideRemoveModal: this.handleHideRemoveModal, projectName: this.props.projectName}) : null))
        );
    }
});

var renderFilesPanel = function (isLogged) {
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
        $('#removeModalBtn').attr('disabled', false);
    } else {
        $('#importModalBtn').attr('disabled', true);
        $('#removeModalBtn').attr('disabled', true);
    }
};

