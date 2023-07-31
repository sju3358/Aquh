echo '빌드 시작'
./gradlew build

echo '도커파일 이미지 빌드'
sudo docker build -t springbootapp .

echo '컨테이너 중지'
sudo docker stop springbootapp

echo '컨테이너 삭제'
sudo docker rm springbootapp

echo '컨테이너 실행'
sudo docker run -p 8080:8080 --name springbootapp --network ubuntu_default -d springbootapp
