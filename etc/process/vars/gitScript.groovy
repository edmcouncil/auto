#!/usr/bin/env groovy


def checkOutGitRepos() {

  final String gitCredentialsId = 'GitHubCredentialsId'

  dir('input') {
    dir("${env.ONTPUB_FAMILY}") {
      //
      // The <YOUR ONTOLOGY> repo is the main repo which also has configuration in the global variable scm,
      // inherit those settings and add some more here.
      //
      checkout([
        $class                           : 'GitSCM',
        branches                         : scm.branches,
        doGenerateSubmoduleConfigurations: false,
        extensions                       : scm.extensions + [
          [$class: 'LocalBranch', localBranch: '**'],
          [$class: 'CheckoutOption', timeout: 1],
          [$class: 'AuthorInChangelog'],
          [$class: 'PruneStaleBranch'],
          [$class: 'IgnoreNotifyCommit']
        ],
        submoduleCfg                     : [],
        userRemoteConfigs                : scm.userRemoteConfigs + [
          [credentialsId: gitCredentialsId, url: "https://github.com/edmcouncil/${env.ONTPUB_FAMILY}.git"]
        ]
      ])
    }
  }
}

return this