echo '컨테이너 중지'
docker stop reactapp

echo '컨테이너 삭제'
docker rm reactapp

echo '이미지 삭제'
docker rmi reactapp

echo '도커파일 이미지 빌드'
docker build -t reactapp .

echo '컨테이너 실행'
docker run -p 3000:3000 --name reactapp --network ubuntu_default -d reactapp
