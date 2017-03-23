//var converter = new Showdown.converter();

var MainComponent = React.createClass({className: "mainComponent",
    
    render: function () {
        return (React.createElement('div', {className: 'maincomponent'},
                React.createElement(JoinUsBtn),
                React.createElement(FilesList),
                React.createElement(PlotSpace),
                React.createElement(ViewPanel)
            )
          );
    }
})

var renderMainComponent = function () {
    React.render(
        React.createElement(MainComponent),
        document.getElementById("main")
    );
};


var FilesList = React.createClass({className: "filesList",
    
    
    render: function () {
        var cardStyle = {
        height: 200,
        width: 200,
        padding: 0,
        float: "left",
        backgroundColor: "#FFF",
        WebkitFilter: "drop-shadow(0px 0px 5px #666)",
        filter: "drop-shadow(0px 0px 5px #666)"
    }
        return (React.createElement('div', {className: 'filesList', style: cardStyle},
                React.createElement("h1", null, "Files"),
                React.createElement("a", {href:  "#", id: "file1"}, "file1.fasta"),
                React.createElement('br'),
                React.createElement("a", {href:  "#", id: "file2"}, "file2.fasta")
            )
          );
    }
})

var PlotSpace = React.createClass({
    handleSaving: function () {
        alert("This button will log in the user soon :)")
    },
        render: function () {
        var cardStyle = {
        height: 450,
        width: 550,
        float: "left",
        backgroundColor: "#FFF",
        WebkitFilter: "drop-shadow(0px 0px 5px #666)",
        filter: "drop-shadow(0px 0px 5px #666)"
        }   
        
        var imgStyle = {
           height: 400,
           width: 500,
           padding: "25px 25px 25px 25px"
        }   
        
        var btnStyle = {
           float: "right"
        }  
        
        return (React.createElement("form", {className: "imageSpace", style: cardStyle},
                React.createElement("image", {src: "http://veronicaraulin.com/wp-content/uploads/2015/10/cropped-bigstock-Loading-Bar-With-Bulb-Creativ-94955525.jpg",
                style: imgStyle}),
                React.createElement("button", {className: "saveImgBtn", onClick: this.handleSaving, style: btnStyle}, "Save Circos")
            )
          );
    
    }
})

var SigninForm = React.createClass({displayName: "LoginForm",
    handleSubmit: function () {
        alert("This button will log in the user soon :)")
    },
    render: function () {
        var cardStyle = {
        height: 200,
        width: 300,
        padding: 0,
        backgroundColor: "#FFF",
        WebkitFilter: "drop-shadow(0px 0px 5px #666)",
        filter: "drop-shadow(0px 0px 5px #666)",
      }
        return (
            React.createElement("form", {className: "loginForm", style: cardStyle, onSubmit: this.handleSubmit},
                React.createElement("h1", null, "Sign in"),
                React.createElement("label", {type: "text", htmlFor: "login"}, "Login: "),
                React.createElement("input", {type: "text", placeholder: "Login", ref: "login", id: "login"}),
                React.createElement('br'),
                React.createElement("label", {type: "text", htmlFor: "password"}, "Password: "),
                React.createElement("input", {type: "text", placeholder: "Password", ref: "text", id: "password"}),
                React.createElement('br'),
                React.createElement("input", {type: "submit", value: "Sign in"}),
                React.createElement('br'),
                React.createElement("label", {type: "text", htmlFor: "signUpAnch"}, "Don't have an account? "),
                React.createElement('br'),        
                React.createElement("a", {href:  "#", id: "signUpAnch"}, "Sign up!")
                )
        );
    }
});

var renderSignin = function () {
    React.render(
        React.createElement(SigninForm),
        document.getElementById("main")
    );
};

var SignupForm = React.createClass({displayName: "RegistrationForm",
    handleSubmit: function (e) {
        alert("This button will register new user in database soon :)")
    },
    render: function () {
        var cardStyle = {
        height: 200,
        width: 300,
        padding: 0,
        backgroundColor: "#FFF",
        WebkitFilter: "drop-shadow(0px 0px 5px #666)",
        filter: "drop-shadow(0px 0px 5px #666)",
      }
        return (
            React.createElement("form", {className: "registrationForm", style: cardStyle, onSubmit: this.handleSubmit},
                React.createElement("h1", null, "Sign up"),
                React.createElement("label", {type: "text", htmlFor: "login"}, "Login: "),
                React.createElement("input", {type: "text", placeholder: "Login", ref: "login", id: "login"}),
                React.createElement('br'),
                React.createElement("label", {type: "text", htmlFor: "password"}, "Password: "),
                React.createElement("input", {type: "text", placeholder: "Password", ref: "text", id: "password"}),
                React.createElement('br'),
                React.createElement("label", {type: "text", htmlFor: "name"}, "Name: "),
                React.createElement("input", {type: "text", placeholder: "Name", ref: "text", id: "name"}),
                React.createElement('br'),
                React.createElement("label", {type: "text", htmlFor: "email"}, "E-mail "),
                React.createElement("input", {type: "text", placeholder: "Email", ref: "text", id: "email"}),
                React.createElement('br'),
                React.createElement("input", {type: "submit", value: "Register"})
                )
        );
    }
});

var UploadForm = React.createClass({displayName: "UploadForm",
    handleSubmit: function() {
        this.refs.file.getDOMNode().value = '';
        event.preventDefault();
        this.setState({ buttonDisabled: true });
    },

    render: function() {
        var cardStyle = {
        height: 300,
        width: 300,
        padding: 0,
        backgroundColor: "#FFF",
        WebkitFilter: "drop-shadow(0px 0px 5px #666)",
        filter: "drop-shadow(0px 0px 5px #666)",
      }
        return (
          React.createElement("form", {className: "registrationForm", style: cardStyle, onSubmit: this.handleSubmit},
                React.createElement("h1", null, "Upload file"),
                React.createElement("label", {type: "text", htmlFor: "file"}, "Choose file: "),
                React.createElement("input", {type: "file", placeholder: "Upload", ref: "file", id: "file"}),
                React.createElement("input", {type: "submit", value: "Upload file", id: "upload"}),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "refSeq", id: "refSeqChbox"}),
                React.createElement("label", {type: "text", htmlFor: "refSeqChbox"}, "Reference sequence"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "alignment", value: "Alignment", id: "alignChbox"}),
                React.createElement("label", {type: "text", htmlFor: "alignChbox"}, "Alignment"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "variants", value: "Variants", id: "variantsChbox"}),
                React.createElement("label", {type: "text", htmlFor: "variantsChbox"}, "Variants"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "exprLvls", value: "Expression levels", id: "expLvlsChbox"}),
                React.createElement("label", {type: "text", htmlFor: "expLvlsChbox"}, "Expression levels"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "diffExpr", value: "Differential expression", id: "diffExprChbox"}),
                React.createElement("label", {type: "text", htmlFor: "diffExprChbox"}, "Differential expression"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "annotation", value: "Annotation", id: "annotationChbox"}),
                React.createElement("label", {type: "text", htmlFor: "annotationChbox"}, "Annotation")
        )
        );
    }
});

var UserWelcome = React.createClass({className: "welcomeLabel",
    
    
    render: function () {
        return (React.createElement('div', {className: 'welcome'},
                React.createElement("h1", null, "Welcome " + this.props.login + "!")
            )
          );
    }
})

var JoinUsBtn = React.createClass({className: "joinUsBtn",
        handleSubmit: function () {
        alert("This button will open login pop-up window soon :)")
    },
    render: function () {
        return (React.createElement("form", {className: "loginBtn"},
                React.createElement("input", {type: "submit", value: "Join us", onClick: this.handleSubmit})
            )
          );
    }
})

var ViewPanel = React.createClass({displayName: "UploadForm",
    handleSubmit: function() {
    },

    render: function() {
        var cardStyle = {
        float: "left",
        height: 150,
        width: 300,
        padding: 0,
        backgroundColor: "#FFF",
        WebkitFilter: "drop-shadow(0px 0px 5px #666)",
        filter: "drop-shadow(0px 0px 5px #666)",
      }
        return (
          React.createElement("form", {className: "viewPanel", style: cardStyle},
                React.createElement("h1", null, "View Settings"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "readsDepth", value: "Reads Depth", id: "rdepthChbox"}),
                React.createElement("label", {type: "text", htmlFor: "rdepthChbox"}, "Reads Depth"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "snpDens", value: "SNP Density", id: "snpDensChbox"}),
                React.createElement("label", {type: "text", htmlFor: "variantsChbox"}, "Variants"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "exprLvls", value: "Expression levels", id: "expLvlsChbox"}),
                React.createElement("label", {type: "text", htmlFor: "expLvlsChbox"}, "Expression levels"),
                React.createElement('br'),
                React.createElement("input", {type: "checkbox", name: "diffExpr", value: "Differentially expressed regions", id: "diffExprChbox"}),
                React.createElement("label", {type: "text", htmlFor: "diffExprChbox"}, "Differentially expressed regions")
        )
        );
    }
});
