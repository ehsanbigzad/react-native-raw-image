#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RawImage, NSObject)

RCT_EXTERN_METHOD(rawToPng:(NSString)path
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

@end
