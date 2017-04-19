/* global React */

var converter = new Showdown.converter();
//funkcja dla IE
String.prototype.endsWith = function (suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};
var ImportModalTest = React.createClass({className: "importModalTest",
    componentDidMount: function componentDidMount()
    {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideImportModal);
    },
    handleSubmit: function(){
        alert('This button will import file from another project');
    },
    render: function () {
        return (React.createElement('div', {className: 'modal fade'},
                React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close',
                                            'data-dismiss': 'modal', 'aria-label': 'Close'},
                                                React.createElement('span', {'aria-hidden': 'true'}, '\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Import file')),
                                React.createElement('div', {className: 'modal-body', id: 'uploadPanelBody'},
                                        
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},
                                                'Upload file')))
                        ))
                ));
    },
    propTypes: {
        handleHideUploadModal: React.PropTypes.func.isRequired,
        projectName: React.PropTypes.string
    }
});
