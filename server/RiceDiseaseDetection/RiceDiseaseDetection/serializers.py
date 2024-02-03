####python object ot json
from rest_framework import serializers
from .models import ImageInfo

class ImageInfoSerializer(serializers.ModelSerializer):
    class Meta:
        model = ImageInfo
        fields = ['id', 'image_base64', 'prediction']  ### id automatically added