var converter = new Showdown.converter();

var UploadModal = React.createClass({className: "uploadModal",
    componentDidMount: function componentDidMount() {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideUploadModal);
    },
    handleSubmit: function (e) {
        e.preventDefault();
        var files = this.refs.fileUpload.getDOMNode().value.trim();

        console.log(files);

    },
    render: function () {
        return (React.createElement('div', {className: 'modal fade'},
                React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close',
                                            'data-dismiss': 'modal', 'aria-label': 'Close'},
                                                React.createElement('span', {'aria-hidden': 'true'}, '\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Upload file')),
                                React.createElement('div', {className: 'modal-body'},
                                        React.createElement('h4', {className: 'modal-title'}, 'Choose file: '),
                                        React.createElement('input', {type: 'file', ref: 'fileUpload'}),
                                        React.createElement("hr"),
                                        React.createElement('h4', {className: 'modal-title'}, 'What is file content? '),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isSeq'}, " Sequence"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isAlign'}, " Alignment"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isVar'}, " Variants"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isExpr'}, " Expression"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isDiffExpr'}, " Differential expression")),
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},
                                                'Upload file')))
                        ))
                );
    },
    propTypes: {
        handleHideUploadModal: React.PropTypes.func.isRequired
    }
})