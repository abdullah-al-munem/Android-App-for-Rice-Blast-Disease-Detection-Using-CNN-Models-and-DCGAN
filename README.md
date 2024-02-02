# Android App for Rice Blast Disease Detection Using CNN Models and DCGAN

## Abstract

Rice is one of the staple food all over the world. Many people make their living by planting rice. Blast is one of the prominent rice diseases. Blast disease has destructive effects and farmers have to identify and prevent the disease as early as possible. Automictic disease detection can assist farmers to take action timely and accurately. Automatic detection of Blast disease is a challenging task since there is another rice disease called Brown Spot which looks similar to the Blast disease. In this experiment, we developed a mobile app that can detect Blast and Brown Spot disease using the captured image from the field. The CNN model has been employed to classify rice disease, especially Blast and Brown Spots. Several pre-trained models have been trained and evaluated on a combined dataset. The dataset was created by capturing images from the field and collecting them from multiple sources. Several preprocessing techniques have been used to improve the model's performance. DCGAN has been used to create new synthetic images to enhance the dataset. The obtained result before and after using DCGAN has been compared for determining the improvement of the model performance. The experimental result shows that InceptionResNet V2 outperforms the other pre-trained model after augmenting the images using DCGAN. The InceptionResNet V2 achieved the highest accuracy of 0.911, precision of 0.922, and recall of 0.900 on the combined dataset after the augmentation using DCGAN. The InceptionResNet V2 model has been integrated with the mobile app using Django REST API. The app can detect diseases and give appropriate solutions to farmers. This app can reduce the human interaction for identifying rice disease which eventually makes the disease detection process faster and more efficient. Since the detection process requires only mobile devices, thus this project has no harmful social or environmental impact. This project significantly improves the accuracy for detecting rice blast disease and shows that using CNN-based augmentation techniques such as DCGAN can improve the model performance. 

## Read the Paper
```
https://www.researchgate.net/publication/375573604_Rice_Blast_Disease_Detection_Using_CNN_Models_and_DCGAN
```
##  Citation 
```
@inproceedings{al2023rice,
  title={Rice Blast Disease Detection Using CNN Models and DCGAN},
  author={Al Munem, Abdullah and Maha, Lamyea Tasneem and Haque, Rafid Mahmud and Safa, Noor Fabi Shah and Khan, Mozammel HA and Khan, Mohammad Ashik Iqbal},
  booktitle={International Conference on Intelligent Computing \& Optimization},
  pages={231--242},
  year={2023},
  organization={Springer}
}
```
##  Dataset
1.  Classification: https://www.kaggle.com/datasets/abdullahalmunem/rice-diseases-leaf-blast-classification
2.  DCGAN: https://www.kaggle.com/datasets/abdullahalmunem/datasetfordcgan
