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
                                            React.createElement('span',{ 'aria-hidden': 'true' },'\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Upload file')),
                                React.createElement('div', {className: 'modal-body'},
                                        React.createElement('input', {type: 'file', ref: 'fileUpload'})),
                                React.createElement('div',{ className: 'modal-footer' },
                                React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},
                                'Upload file')))
                ))
                );
    },
    
    propTypes: {
        handleHideUploadModal: React.PropTypes.func.isRequired
      }
})