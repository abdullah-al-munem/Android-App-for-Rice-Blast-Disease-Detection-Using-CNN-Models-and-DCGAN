from tkinter import Image
from django.http import HttpResponse
import re
from django.http import JsonResponse
from .models import ImageInfo
from .serializers import ImageInfoSerializer
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status

from RiceDiseaseDetection import serializers

from PIL import Image
import base64
import tensorflow as tf
import keras
import io
import numpy as np




# model = keras.models.load_model('..\RiceDiseaseDetection\Proposed_Model_v6.h5')

model = keras.models.load_model('Proposed_InceptionResNetV2_Model_50.h5')

def home_page(request):
    return HttpResponse('home ')



@api_view(['GET','POST','DELETE'])
def image_lists(request, format =None):

    if request.method == 'GET':  # get/read
        all_images = ImageInfo.objects.all()
        serializer = ImageInfoSerializer(all_images, many=True)
        print("inside get_image_list")
        return Response(serializer.data)

    if request.method == 'DELETE':
        all_images = ImageInfo.objects.all()
        all_images.delete()
        return Response(status = status.HTTP_204_NO_CONTENT)


    if request.method =='POST':  #you want to add a data and do some operations on it
        #print(request.data["description"])
        image_base64 = request.data["image_base64"]
        image = Image.open(io.BytesIO( base64.b64decode( image_base64 ) ) ).resize( ( 224, 224) ).convert( 'RGB')
        image = np.asarray( image )
        image = np.expand_dims( image , axis=0 ) / 255.0
        pred = model.predict(image)

        print(pred)

        prediction_result = "none";
        predicted_image = max(pred[0][0], pred[0][1], pred[0][2], pred[0][3])
        if(predicted_image == pred[0][0]): prediction_result = "Brown Spot"
        elif(predicted_image == pred[0][1]): prediction_result = "Healthy leaf"
        elif(predicted_image==pred[0][2]): prediction_result = "Invalid input"
        elif(predicted_image==pred[0][3]): prediction_result = "Leaf Blast"
        else: prediction_result = "none"

        print("prediction result is: "+prediction_result)
        request.data["prediction"] = prediction_result

        serializer = ImageInfoSerializer(data = request.data)
        if serializer.is_valid(raise_exception='True'):
            serializer.save()
            
            return Response(serializer.data, status=status.HTTP_201_CREATED)


    



