<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
    Document   : MainWindow
    Created on : 15-Mar-2017, 10:25:14
    Author     : s262012
--%>

<html>
    <head>
        <title>SeqCircos</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <script src="${contextPath}/resources/js/vendor/react.js"></script>
        <script src="${contextPath}/resources/js/vendor/showdown.min.js"></script>
        <script src="${contextPath}/resources/js/home.js"></script>
        <script src="${contextPath}/resources/js/modals/loginModal.js"></script>
        <script src="${contextPath}/resources/js/modals/registrationModal.js"></script>
        <script src="${contextPath}/resources/js/modals/resetPswdModal.js"></script>
        <script src="${contextPath}/resources/js/dynamic/accountDropdown.js"></script>
        <script src="${contextPath}/resources/js/panels/filesPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/projectsPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/circosPanel.js"></script>
        <script src="${contextPath}/resources/js/dynamic/welcomeHeader.js"></script>
        <script src="${contextPath}/resources/js/modals/changePswdModal.js"></script>
        <script src="${contextPath}/resources/js/modals/uploadModal.js"></script>
        <script src="${contextPath}/resources/js/panels/viewPanel.js"></script>
        <script src="${contextPath}/resources/js/modals/newProjectModal.js"></script>
        <script src="https://rawgit.com/enyo/dropzone/master/dist/dropzone.js"></script>
        <link rel="stylesheet" href="https://rawgit.com/enyo/dropzone/master/dist/dropzone.css">
    </head>
    <body>
        <div class="row">  
            <security:authorize acess="isAuthenticated"></security>
            
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <div id="upperLeftContainer" class="col-lg-3 page-header" style="float:left;margin:0px 20px 0px 30px">${upperLeftContainer} 
                    <h2>Welcome ${pageContext.request.userPrincipal.name}!</h2>
                </div>
                <div id="upperRightContainer" class="col-lg-3" style="float:right;margin:0px 40px 0px 40px">${upperRightContainer}</div>
                <script type="text/javascript">
                    $(function () {
                        renderAccountDropdown();
                    });
                </script>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <div id="upperLeftContainer" class="col-lg-3" style="float:left;margin:0px 40px 20px 40px">${upperLeftContainer}
                    <script type="text/javascript">
                        $(function () {
                            renderHomePage();
                        });
                    </script>
                </div>
            </c:if>
        </div>
        <div class="row" style="margin:0px 20px 0px 20px">
            <div class="row">
                <div class="col-lg-3" style="float:left">
                    <div id="projectsContainer" class="row" style="margin:0px 20px 0px 20px">${projectsContainer}</div>
                    <script type="text/javascript">
                        $(function () {
                            renderProjectsPanel();
                        });
                    </script>
                    <div id="filesContainer" class="row" style="margin:0px 20px 0px 20px">${filesContainer}</div>
                    <script type="text/javascript">
                        $(function () {
                            renderFilesPanel();
                        });
                    </script>
                </div>
                <div id="centerContainer" class="col-lg-6">${centerContainer}</div>
                <script type="text/javascript">
                    $(function () {
                        renderCircosPanel();
                    });
                </script>
                <div id="rightContainer" class="col-lg-2" style="float:left;margin:0px 20px 0px 20px">${rightContainer}</div>
                <script type="text/javascript">
                    $(function () {
                        renderViewPanel();
                    });
                </script>
            </div>
        </div>    
    </body>
</html>
