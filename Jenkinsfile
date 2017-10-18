def APP_NAME = 'sample-app'
def JDK_DOCKER_IMAGE_NAME = 'openjdk:8-jre'
node {
    // 変数宣言(宣言だけ)
    def contextPath = ''
    def containerName = ''
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
    stage('Start Application in Docker container.') {
      def branchName = getBranchName()
      contextPath = '/' + APP_NAME + '/' + branchName
      def localJarDir = '/var/tmp' + contextPath
      containerName = APP_NAME + '_' + branchName
      // 既存のコンテナがあれば削除
      sh "docker rm -f ${containerName} || echo 'Container already exists. Delete container.'"
      // あろうがなかろうが「Jarを置くディレクトリ」を再作成
      sh "rm -rf ${localJarDir} && mkdir -p ${localJarDir}"
      // 予め作っておいたJarを移動
      sh "mv ./build/libs/*.jar ${localJarDir}/app.jar"

      // dockerコンテナを生成して、SpringBootアプリを起動。
      // その際、コンテキストパスを /[任意の名前]/[branch名] に変更
      def cmd = "docker run -d --rm --name ${containerName} -v ${localJarDir}:/usr/src/myapp -w /usr/src/myapp ${JDK_DOCKER_IMAGE_NAME} java -jar ./app.jar --server.contextPath=${contextPath}"
      sh cmd
    }
    stage('Publish Application per branch by WebServer.') {
      // Dockerのコンテナ名から「内部のIPアドレス」を割り出し。
      def containerIp = getIpAddressByContainerName(containerName)
      // Nginxの設定ファイルとして「内部のコンテナを末尾ポートを削除した状態」で外へ公開する。
      sh "echo 'location ${contextPath} { proxy_pass http://${containerIp}:8080${contextPath}; }' > /etc/nginx/default.d/${containerName}.conf"
      // Nginxの管理コマンドを叩いて「設定ファイルの読みなおし」をさせる。
      sh 'nginx -s reload'
    }
}

/**
 * ブランチ名を取得する関数。
 */
def getBranchName() {
  def branch = env.JOB_NAME.replaceAll(/.*\//,"")
  branch = java.net.URLDecoder.decode(branch, "UTF-8"); // Job名から類推すると「URLエンコードがかかった状態」で取得されるのでデコード。
  return branch
}

/**
 * Dockerのコンテナ名から、IPAddressを割り出す。
 */
def getIpAddressByContainerName(name) {
    return sh (
        script:  "docker inspect --format '{{ .NetworkSettings.IPAddress }}' ${name}"
        ,returnStdout: true
    ).trim()
}
