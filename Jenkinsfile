def APP_NAME = 'sample-app'
def JDK_DOCKER_IMAGE_NAME = 'openjdk:8-jre'
node {
    stage('Checkout Sources') {
      checkout scm
    }
    stage('Unit Test') {
      sh './gradlew clean test'
      step([$class: 'JUnitResultArchiver', testResults: 'build/test-results/test/*.xml']) // JUnitテストレポートを保存
    }
    stage('Jar Build') {
      sh './gradlew build'
    }
    stage('Jar Build') {
      sh './gradlew build'
    }
    stage('Start App in Docker container.') {
      def branchName = getBranchName()
      def contextPath = '/' + APP_NAME + '/' + branchName
      def localJarDir = '/var/tmp' + context
      def containerName = APP_NAME + '_' + branchName

      // 既存のコンテナがあれば削除
      sh "docker rm -f ${containerName} || echo 'Container already exists. Delete container.'"
      // あろうがなかろうが「Jarを置くディレクトリ」を再作成
      sh "rm -rf ${localJarDir} && mkdir -p ${localJarDir}"
      // 予め作っておいたJarを移動
      sh "mv ./build/libs/*.jar ${localJarDir}/app.jar"
      // dockerコンテナを生成して、SpringBootアプリを起動。
//      sh "docker run --rm --name ${containerName} -v ${localJarDir}:/usr/src/myapp -w /usr/src/myapp ${JDK_DOCKER_IMAGE_NAME} java -jar ./*.jar --server.contextPath=${}"
      def cmd = "docker run --name ${containerName} -v ${localJarDir}:/usr/src/myapp -w /usr/src/myapp ${JDK_DOCKER_IMAGE_NAME} java -jar ./app.jar --server.contextPath=${contextPath}"
      echo 'execute command:' + cmd
      sh cmd
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
