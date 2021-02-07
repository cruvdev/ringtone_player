#import "RingtonePlayerPlugin.h"
#if __has_include(<ringtone_player/ringtone_player-Swift.h>)
#import <ringtone_player/ringtone_player-Swift.h>
#import <AudioToolbox/AudioToolbox.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "ringtone_player-Swift.h"
#endif

@implementation RingtonePlayerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftRingtonePlayerPlugin registerWithRegistrar:registrar];
    FlutterMethodChannel* channel = [FlutterMethodChannel
                                     methodChannelWithName:@"flutter_ringtone_player"
                                     binaryMessenger:[registrar messenger]];
    FlutterRingtonePlayerPlugin* instance = [[FlutterRingtonePlayerPlugin alloc] init];
    [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if ([@"play" isEqualToString:call.method]) {
        NSNumber *soundId = (NSNumber *)call.arguments[@"ios"];
        AudioServicesPlaySystemSound([soundId integerValue]);
        result(nil);
    } else if ([@"stop" isEqualToString:call.method]) {
        result(nil);
    } else {
        result(FlutterMethodNotImplemented);
    }
}

@end
