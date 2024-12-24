# CODE-PULSE
This folder is for frontend, that it is a part of microservices with the whole project repository.
and have been generated with .

## Technologies stack
<ul>
    <li>Angular 17</li>
    <li>[Angular CLI](https://github.com/angular/angular-cli) version 17.2.2</li>
    <li>Spring boot 3.4.1</li>
</ul>

## Prerequisites
Before working with this project, you must have installed and configured on your machine: 
<ul>
  <li>Node 20</li>
  <li>NPM latest & stable</li>
  <li>Docker Engine v25.0.3 && Docker Desktop v4.28.0</li>
  <li>Docker Sync latest & stable</li>
  <li>Kubernetes or Minikube latest & stable</li>
  <li>OpenSSL</li>
</ul>

## Resources hierarchy of this project
<ul>
    <li>
        Frontend : contains frontend microservices
        <ul>
            <li>front-hub-ui : This the public hub landing for this project</li>
        </ul>
    </li>
    <li>
        Backend : contains backend microservices
        <ul>
            <li>code-pulse-api : This is the api that handle code pulse related objects</li>
        </ul>
    </li>
</ul>

## Gitflow
Our workflow is based on using standards, obviously branches and tags of versions.
We do have Branches as following:
<ul>
  <li>main : The latest stable release on production.</li>
  <li>uat : For the User Acceptance Test.</li>
  <li>develop : For current developments.</li>
  <li>feature/xxxx : For the new features related to app. the name of branch must starts with prefix "feature/" and tracks origin/develop, and "xxx" is the significant title composed of the number of ticket and short brief title.</li>
  <li>fix/xxxx : Same as features, but this for bugs related monitored on develop, uat and staging branch, it must tracks origion/develop also and "xxx" as explained previously.</li>
  <li>hotfix/xxxx : Same as previous, but the difference here is it must tracks main, because it is related to urgent and hot bugs monitored on the production.</li>
</ul>

Working progress must be committed and pushed every day.
If work is done on feature/xxxx or fix/xxxx to develop, you have to initialize Merge Request using Github UI. From your branch to develop.
The title of the Merge Request must be as this : [FEATURE|FIX|HOTFIX][FRONTEND|BACKEND|SOCLE] : Some comments.

If the work is done on hotfix/xxxx, you have to do the same but the destination of merge request is staging the title will be :
[HOTFIX][FRONTEND|BACKEND|SOCLE] . And manage to integrate the correction inside new developments on develop branch.

If your Merge Request is still in progress you can add Draft: prefix to begining of title of MR.

