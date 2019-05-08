
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Please sign in</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>

    <style>
        .top {
            text-align: center
        }
        .title {
            font-size: 33px;
            color: rgba(0,0,0,.85);
            font-family: Myriad Pro,Helvetica Neue,Arial,Helvetica,sans-serif;
            font-weight: 600;
            position: relative;
            top: 2px;
        }
        .desc {
            font-size: 14px;
            color: rgba(0,0,0,.45);
            margin-top: 12px;
            margin-bottom: 40px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="top">
        <div class="header">
            <h1 class="title">Login page</h1>
        </div>
        <div class="desc">
            Example user: user / password
        </div>
    </div>

    <form class="form-signin" method="post" action="/auth/login">
        <#--<h2 class="form-signin-heading">Please sign in</h2>-->
        <p>
            <label for="username" class="sr-only">Username</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
        </p>
        <p>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        </p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>
</body></html>