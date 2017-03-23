var converter = new Showdown.converter();

var ViewBox = React.createClass({className: "viewBox",
    
    handleSubmit: function (e) {
         e.preventDefault();
        var files = this.refs.fileUpload.getDOMNode().value.trim();

        console.log(files);

    },
//    componentDidMount: function () {
//        var self = this;
//        // disable dropzone autodiscover
//        Dropzone.autoDiscover = false;
//        // istantiate dropzone
//        var myDropzone = new Dropzone(this.getDOMNode(), {
//            url: '/api/upload'
//        });
//        myDropzone.on("complete", function (file) {
//            // callback on Album class
//            self.props.onUploadComplete(file);
//        });
//    },
    render: function () {
        return (
                React.createElement('div', {className: "panel panel-primary"},
                        React.createElement('div', {className: "panel-heading"}, "View settings"),
                        React.createElement('div', {className: "panel-body"},
//                        React.createElement("form", {action: "/api/upload", className: "dropzone", id: "dropzone"},
//                                React.createElement("div", {className: "dz-default dz-message text-center"},
//                                        React.createElement("i", {className: "fa fa-cloud-upload fa-4x"})))
                            React.createElement("h5", null, 'This panel will contain view settings soon')
                )));
    }
})

var renderViewBox = function () {
    React.render(React.createElement(ViewBox), document.getElementById('rightContainer'));
};

