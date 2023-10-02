# ansible-k8s-virtualbox

Task için proje ansible da oluşturulmuştur. Bilgisayarım windows tabanlı olduğu için ilk iki stepin çalıştırılması yani 
virtualbox kurulumu ve makine oluşturma işlemini  ansible komutlarının çalıştırılması için "winrm" kullanılmıştır. 
**inventories> local.kube** projenin inventori kısmıdır ve makine bilgileri, değişkenler vs. bu kısımda yer alır.

roles kısmında kurulum dosyaları olup, docker , jenkins, dockerregistry, maven,  java vs. kurulum komutları yer almaktadır.

Projenin çalıştırılması için ; 

1. step için : ansible-playbook -i inventories/local.kube/ 1.vbox.yml

2. step : ansible-playbook -i inventories/local.kube/ 2.createVm.yml

3. step: ansible-playbook -i inventories/local.kube/ 3.k8s.yml

4. step: ansible-playbook -i inventories/local.kube/ 4.jenkins.yml

5. step: ansible-playbook -i inventories/local.kube/ 5.registry.yml

6. step : bunun gerçekleştirilmesi için java da proje oluşturulup Dockerfile yazılmıştır. 
   docker image için java kodu  : https://github.com/hkgunes/docker_app.git
   pipelinenin oluşturulması için **roles > jenkins > files** içerisinde **Jenkinsfile.groovy** dosyası var olup,pipeline adımlarını içerir.
   Sırasıyla ;
    - kodu githupdan çeker ve maven ile derler
    - image oluşturulur 
    - oluşsan image docker registry'a push'lar
    - kubernetes oluşan image apply eder. **roles>k8s>init>templates>deployment.yml** içerisinde deploy edeceği image bilgisi bulunur. 
    