var converter = new Showdown.converter();

var FilesPanel = React.createClass({className: "filesPanel",
    
    getInitialState: function getInitialState() {
        return { view: { showUploadModal: false } };
    },
    
    handleShowUploadModal: function handleShowUploadModal() {
    this.setState({ view: { showUploadModal: true } });
    },
    handleHideUploadModal: function handleHideUploadModal() {
        this.setState({ view: { showUploadModal: false } });
        $( ".modal-backdrop.in" ).remove();
    },
  
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Files  ", 
                                React.createElement('button', {type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowUploadModal},
                                        React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true' }))),
                React.createElement('div', {className: "panel-body"},
                        React.createElement('div', {className: "panel panel-success"},
                                React.createElement('div', {className: "panel-heading"}, "Sequence"),
                                React.createElement('div', {className: "panel-body"}, "sequencefile1")),
                        React.createElement('div', {className: "panel panel-info"},
                                React.createElement('div', {className: "panel-heading"}, "Alignment"),
                                React.createElement('div', {className: "panel-body"}, "alignmentfile1")),
                        React.createElement('div', {className: "panel panel-warning"},
                                React.createElement('div', {className: "panel-heading"}, "Variants"),
                                React.createElement('div', {className: "panel-body"}, "variantfile1")),
                        React.createElement('div', {className: "panel panel-danger"},
                                React.createElement('div', {className: "panel-heading"}, "Expression"),
                                React.createElement('div', {className: "panel-body"}, "expressionfile1"))
                        ),
                        this.state.view.showUploadModal ? React.createElement(UploadModal, { handleHideUploadModal: this.handleHideUploadModal }) : null
                ));
    }
})

var renderFilesPanel = function () {
    React.render(React.createElement(FilesPanel), document.getElementById('leftContainer'));
};

