node {
    stage('Checkout Sources') {
      checkout scm
    }
    stage('Unit Test') {
      sh './gradlew clean test'
    }
    stage('Jar Build') {
      sh './gradlew build'
    }
}

/*
 * ブランチ名を取得する関数。
 */
def getBranchName() {
  def branch = env.JOB_NAME.replaceAll(/.*\//,"")
  branch = java.net.URLDecoder.decode(branch, "UTF-8"); // Job名から類推すると「URLエンコードがかかった状態」で取得されるのでデコード。
  return branch
}
