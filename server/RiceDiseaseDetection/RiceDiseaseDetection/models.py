from unittest.util import _MAX_LENGTH
from django.db import models

class ImageInfo(models.Model):
    image_base64 = models.TextField()
    prediction = models.CharField(default="none", null = True, max_length = 200)

    def __str__(self):
        return self.name + ' ' + self.description  #to see name instead of object(1)
